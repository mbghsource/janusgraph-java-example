package pluradj.janusgraph.example;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.attribute.Geo;
import org.janusgraph.core.attribute.Geoshape;
import org.janusgraph.example.GraphOfTheGodsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class JavaExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaExample.class);

    /* public static void main(String[] args) {
       JavaExample self = new JavaExample()
       self.runMe(args);
    }
    public void runMe(String[] args) { */

    public static void main(String[] args) {
        JanusGraph graph = JanusGraphFactory.open("conf/janusgraph-berkeleyje-lucene.properties");
        GraphTraversalSource g = graph.traversal();
        if (g.V().count().next() == 0) {
            // load the schema and graph data
            GraphOfTheGodsFactory.load(graph);
        }
        Map<String, ?> saturnProps = g.V().has("name", "saturn").valueMap(true).next();
        LOGGER.info(saturnProps.toString());
        List<Edge> places = g.E().has("place", Geo.geoWithin(Geoshape.circle(37.97, 23.72, 50))).toList();
        LOGGER.info(places.toString());
        LOGGER.info("=============================================");

        LOGGER.info("num vertices " + g.V().count().next());
        LOGGER.info("num edges " + g.E().count().next());
        LOGGER.info("dumping output of g.V()");
        List<Vertex> vertices = g.V().toList();
        LOGGER.info(vertices.toString());
        for(Vertex v: vertices){
          LOGGER.info("-----------");
          LOGGER.info(v.toString());
          LOGGER.info(v.label());
          Iterator vp = v.properties();
          while(vp.hasNext()){
             LOGGER.info(vp.next().toString());
          }
        }
        LOGGER.info("=============================================");
        LOGGER.info("dumping output of g.E()");
        List<Edge> edges = g.E().toList();
        LOGGER.info(edges.toString());
        for(Edge e: edges){
          LOGGER.info("-----------");
          LOGGER.info(e.toString());
          LOGGER.info(e.label());
          Iterator ep = e.properties();
          while(ep.hasNext()){
             LOGGER.info(ep.next().toString());
          }
        }
        System.exit(0);
    }
}
