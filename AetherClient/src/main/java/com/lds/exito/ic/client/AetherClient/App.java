package com.lds.exito.ic.client.AetherClient;

import org.apache.maven.model.resolution.ModelResolver;
import org.apache.maven.repository.internal.ArtifactDescriptorUtils;
import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.resolution.ArtifactDescriptorException;
import org.eclipse.aether.resolution.ArtifactDescriptorRequest;
import org.eclipse.aether.resolution.ArtifactDescriptorResult;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
	private static Logger log = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args ) throws ArtifactResolutionException, ArtifactDescriptorException
    {
    	 log.debug( "------------------------------------------------------------" );
    	 log.debug( App.class.getSimpleName() );

         RepositorySystem system = Booter.newRepositorySystem();

         RepositorySystemSession session = Booter.newRepositorySystemSession( system );
         
         Artifact artifact = new DefaultArtifact("com.lds.exito.terminal.framework", "Clifre", "", "jar", "0.0.1-SNAPSHOT");

         ArtifactRequest artifactRequest = new ArtifactRequest();
         artifactRequest.setArtifact( artifact );
         artifactRequest.setRepositories( Booter.newRepositories( system, session ) );

         ArtifactResult artifactResult = system.resolveArtifact( session, artifactRequest );

         artifact = artifactResult.getArtifact();

         log.debug( artifact + " resolved to  " + artifact.getFile() );
         log.debug(artifact.getClassifier());
         
         //loading descriptors
         ArtifactDescriptorRequest descriptorRequest = new ArtifactDescriptorRequest();
         descriptorRequest.setArtifact( artifact );
         descriptorRequest.setRepositories( Booter.newRepositories( system, session ) );

         ArtifactDescriptorResult descriptorResult = system.readArtifactDescriptor( session, descriptorRequest );

         for ( Dependency dependency : descriptorResult.getDependencies() )
         {
        	 log.debug( dependency.getArtifact().getGroupId() + " : " + dependency.getArtifact().getArtifactId()
        			 + " : " + dependency.getArtifact().getVersion());
         }
         
    }
}
