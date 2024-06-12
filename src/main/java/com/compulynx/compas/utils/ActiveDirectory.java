package com.compulynx.compas.utils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class ActiveDirectory {
	private static final Logger logger = Logger.getLogger(ActiveDirectory.class.getName());
	   private final String username;
	    private final String passowrd;
	    private final Properties properties;
	    
	    public ActiveDirectory(String path, String username, String password){
	        properties = new Properties();
	        getProperties(path);
	        
	        this.username = createUsername(username);
	        this.passowrd = password;
	    }
	    
	    
	    private Properties createConnectionProperties() {
	        Properties prop = new Properties();
	        prop.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	        prop.put(Context.PROVIDER_URL, this.createUrl());
	        prop.put(Context.SECURITY_AUTHENTICATION,"simple");
	        prop.put(Context.SECURITY_PRINCIPAL, this.getUsername());
	        prop.put(Context.SECURITY_CREDENTIALS,this.getPassword());
	        
	        return prop;
	    }
	    
	    public boolean login() {
	        try {
	            DirContext ctx = new InitialDirContext(createConnectionProperties());
	            ctx.close();
	            return true;
	        }
	        catch(NamingException e){
	            return false;
	        }
	    }
	    private String getUsername() {
	        return this.username;
	    }
	    
	    private String getPassword() {
	        return this.passowrd;
	    }
	    
	    public SearchResult getSearchControls(String search, String searchBase) throws NamingException {
	    	
	    	logger.info("This is the search param =====>>>>"+search);
	        DirContext context = connectToAd();
	        if(context != null){
	        SearchControls searchControls = new SearchControls();
	        searchControls.setReturningObjFlag(true);
	        String[] returnAttributes = { "sAMAccountName", "givenName", "cn", "mail" };
	        
	        searchControls.setReturningAttributes(returnAttributes );
	        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	        String baseFilter = "(&((&(objectCategory=Person)(objectClass=User)))";
	        String filter = baseFilter;
	        //filter += "(samaccountname="+search+"))";
	        filter += "(mail=" + search + "))";
	        NamingEnumeration<?>  answer;
	        SearchResult searchResult;
	        try{
	            String base = (null == searchBase) ? createUrl() : createDC();
	            answer = context.search(base, filter, searchControls);
	            searchResult = (SearchResult)answer.next();
	            
	            return searchResult;
	        }
	        catch(NamingException e){
	            logger.info("Error creating search object "+e.getMessage());
	            return null;
	          }
	        }
	        else {
	            return null;
	        }
	        
	    }
	    
	    
	        private void getProperties(String path) {
   
	            try{
                   FileInputStream fileInputStream = new FileInputStream(path);
	                this.properties.load(fileInputStream);
	            }
	            catch(FileNotFoundException e) {
	                logger.info("Properties file not found");
	            }
	            catch(IOException e){
	            	 logger.info("Error reading the properties file");
	            }
	        }
	        
	        private String getServer() {
	        	logger.info(this.properties.getProperty("ldap.server"));
	            return this.properties.getProperty("ldap.server");
	        }
	        private String getDomain() {
	        	logger.info(this.properties.getProperty("ldap.domain"));
	            return this.properties.getProperty("ldap.domain");
	        }
	        
	        private DirContext connectToAd() {
	            try{
	                return new InitialDirContext(createConnectionProperties());
	            }
	            catch(NamingException e) {
	                return null;
	            }
	        }
	        
	        
	        private String createDC() {
	            char[] namePair = this.getDomain().toUpperCase().toCharArray();
	            String dn = "CN=Users,DC=";
	            for (int i = 0; i < namePair.length; i++) {
	                if (namePair[i] == '.') {
	                        dn += ",DC=" + namePair[++i];
	                        
	                      //  "cn=rcbandit,cn=ServerAdmins,dc=unixmen,dc=com";
	                } else {
	                        dn += namePair[i];
	                }
	            }
	          return dn;
	        }
	        
	        
	        
	        private String createUrl() {
	            return "ldap://"+this.getServer()+"/"+createDC();
	        }
	        
	        private String createUsername(String username) {
	            if(!username.contains("@")) {
	                username = username+"@"+this.getDomain();
	            }
	            
	            return username;
	        }
}