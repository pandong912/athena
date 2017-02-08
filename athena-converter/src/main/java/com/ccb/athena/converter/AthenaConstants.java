package com.ccb.athena.converter;

/**
 * This class provides a central location for framework configuration keys
 * used to retrieve and store Struts configuration settings.
 */
public final class AthenaConstants {

    public static final String ATHENA_OBJECTFACTORY = "athena.objectFactory";
    public static final String ATHENA_OBJECTFACTORY_ACTIONFACTORY = "athena.objectFactory.actionFactory";
    public static final String ATHENA_OBJECTFACTORY_RESULTFACTORY = "athena.objectFactory.resultFactory";
    public static final String ATHENA_OBJECTFACTORY_CONVERTERFACTORY = "athena.objectFactory.converterFactory";
    public static final String ATHENA_OBJECTFACTORY_INTERCEPTORFACTORY = "athena.objectFactory.interceptorFactory";
    public static final String ATHENA_OBJECTFACTORY_VALIDATORFACTORY = "athena.objectFactory.validatorFactory";
    public static final String ATHENA_OBJECTFACTORY_UNKNOWNHANDLERFACTORY = "athena.objectFactory.unknownHandlerFactory";
     
    /** The com.opensymphony.xwork2.util.FileManager implementation class */
    public static final String ATHENA_FILE_MANAGER_FACTORY = "athena.fileManagerFactory";
    
    /** The com.opensymphony.xwork2.util.fs.FileManager implementation class */
    public static final String ATHENA_FILE_MANAGER = "athena.fileManager";
    
    /** Location of additional configuration properties files to load */
    public static final String ATHENA_CUSTOM_PROPERTIES = "athena.custom.properties";
    
    /** The org.apache.struts2.dispatcher.mapper.ActionMapper implementation class */
    public static final String ATHENA_MAPPER_CLASS = "athena.mapper.class";
    
    public static final String DEFAULT_MAPPER_CONFIGURATION = "athena.mapper.mapping";
    
	public static final String ATHENA_MAPPER_COMPOSITE = "athena.mapper.composite";
	
	public static final String ATHENA_MAPPER_CUSTOM = "athena.mapper.custom";
	
	public static final String ATHENA_MAPPER_DIRECT = "athena.mapper.direct";

}

