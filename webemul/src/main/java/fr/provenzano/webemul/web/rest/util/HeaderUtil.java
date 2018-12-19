package fr.provenzano.webemul.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 */
public final class HeaderUtil {

    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    private HeaderUtil() {
    }
    
    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-webApp-alert", message);
        headers.add("X-webApp-params", param);
        return headers;
    }

    public static HttpHeaders deleteIncidentCreationAlert() {
        return createAlert("L'incident a bien été supprimé de la base de données !", "");
    }
    
    public static HttpHeaders createIncidentCreationAlert() {
        return createAlert("L'incident a bien été sauvegardé en base de données !", "");
    }
    
    public static HttpHeaders createIncidentUpdateAlert() {
        return createAlert("L'incident a bien été sauvegardé en base de données !", "");
    }
    
    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert("A new " + entityName + " is created with identifier " + param, param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert("A " + entityName + " is updated with identifier " + param, param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert("A " + entityName + " is deleted with identifier " + param, param);
    }

    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        log.error("Entity processing failed, {}", defaultMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-webApp-error", defaultMessage);
        headers.add("X-webApp-params", entityName);
        return headers;
    }
    
    public static HttpHeaders createEnvironmentUpdateAlert(String param) {
        return createAlert("L'environnement "+ param +" a bien été mis à jour", param);
    }
    
    public static HttpHeaders createEnvironmentExpUpdateAlert(String param) {
        return createAlert("L'EXP "+ param +" a bien été mis à jour", param);
    }
    
    public static HttpHeaders createExpScriptUpdateAlert(String param) {
        return createAlert("Le script de l'EXP "+ param +" a bien été mis à jour", param);
    }
    
    public static HttpHeaders createAgentUpdateAlert(String param) {
        return createAlert("L'agent "+ param +" a bien été mis à jour", param);
    }
    
    public static HttpHeaders createParameterUpdateAlert(String param) {
        return createAlert("Le paramètre "+ param +" a bien été mis à jour", param);
    }
    
}
