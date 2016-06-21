package in.clustergroup;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCluster;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.Ignition;
import org.apache.ignite.lang.IgniteRunnable;

public class SpecicifGroupBroadcase {
	
	public static void main(String[] args) {
		Ignition.setClientMode(true);
		try (Ignite ignite = Ignition.start("/home/amutha/Tools/apache-ignite-fabric-1.6.0-bin/config/default-config.xml")) {
			IgniteCluster cluster = ignite.cluster();
			
			IgniteCompute computeForRemotes = ignite.compute(cluster.forRemotes());
			computeForRemotes.broadcast(new MessagePrinter("I am a remote node"));
			
			IgniteCompute computeForClient = ignite.compute(cluster.forClients());
			computeForClient.broadcast(new MessagePrinter("I am Client Node"));
			
			IgniteCompute computeForOldest = ignite.compute(cluster.forOldest());
			computeForOldest.broadcast(new MessagePrinter("I am Oldest"));
			
			IgniteCompute computeForServer = ignite.compute(cluster.forServers());
			computeForServer.broadcast(new MessagePrinter("I am Server"));
			
			IgniteCompute computeForRandom = ignite.compute(cluster.forRandom());
			computeForRandom.broadcast(new MessagePrinter("I am Random"));
			
			IgniteCompute computeForHost = ignite.compute(cluster.forHost(cluster.localNode()));
			computeForHost.broadcast(new MessagePrinter("I am on the same host as " + cluster.localNode()));
		}
	}
	
	private static class MessagePrinter implements IgniteRunnable {
		
		private static final long serialVersionUID = 1L;
		
		private String message;
		
		public MessagePrinter(String message) {
			this.message = message;
		}

		@Override
		public void run() {
			System.out.println(message);
		}
		
	}
}
