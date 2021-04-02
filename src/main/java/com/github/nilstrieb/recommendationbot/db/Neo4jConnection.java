package com.github.nilstrieb.recommendationbot.db;

import org.neo4j.driver.*;

import static org.neo4j.driver.Values.parameters;

public class Neo4jConnection implements AutoCloseable {

    private static Neo4jConnection connection;

    private final Driver driver;

    public static void create(String uri, String user, String password) {
        connection = new Neo4jConnection(uri, user, password);
    }

    private Neo4jConnection(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() throws Exception {
        driver.close();
    }

    public void createRecommendation(String uid, String name) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("""
                                MERGE (u:User {id:$uid})
                                MERGE (t:Thing {name:$name})
                                CREATE (u) - [:LIKES] -> (t)
                                """,
                        parameters("uid", uid, "name", name));
                return 0;
            });
        }
        System.out.println("inserted :LIKES");
    }

    public void createSub(String uid, String targetId) {
        try (Session session = driver.session()) {
            session.writeTransaction(tx -> {
                tx.run("""
                                MERGE (u:User {id:$uid})
                                MERGE (t:User {id:$target})
                                CREATE (u) - [:SUBS_TO] -> (t)
                                """,
                        parameters("uid", uid, "target", targetId));
                return 0;
            });
        }
        System.out.println("inserted :SUBS_TO");
    }

    public boolean isSubbed(String uid, String targetId) {
        try (Session session = driver.session()) {
            var result = session.writeTransaction(tx -> {
                Result r = tx.run("""
                                MATCH (:User {id:$uid}) - [:SUBS_TO] -> (:User {id:$target})
                                RETURN 
                                """,
                        parameters("uid", uid, "target", targetId));
                return r.single().size();
            });
            System.out.println(result);
        }
        return false;
    }

    public void printGreeting(String message) {
        try (Session session = driver.session()) {
            String greeting = session.writeTransaction(tx -> {
                Result result = tx.run("CREATE (a:Greeting) " +
                                "SET a.message = $message " +
                                "RETURN a.message + ', from node ' + id(a)",
                        parameters("message", message));
                return result.single().get(0).asString();
            });
        }
    }

    public static Neo4jConnection getInstance() {
        return connection;
    }
}
