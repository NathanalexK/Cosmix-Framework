package mg.itu.prom16.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class FormValidation<T> {
    private HttpServletRequest request = null;
    private boolean hasError = false;
    private T model = null;
    private Map<String, String> fieldValues = new HashMap<>();
    private Map<String, String> errors = new HashMap<>();

    public FormValidation () {
    }

    public FormValidation(HttpServletRequest req, Class<T> modelClass ,T model) {

    }

    public String inputFor(String name, String value) {
        String html = "";
        html += " name=\"" + name + "\"";
        html += " value=\"";
        if(request.getParameter(name) != null && !request.getParameter(name).trim().isBlank()) {
            html += request.getParameter(name);
        }
        else {
            html += value;
        }
        html += "\"";
        return html;
    }

    public String errorFor(String name) {
        if(errors.get(name) != null) {
            return "<p style=\"color: red\"> " + errors.get(name) + "</p>";
        }
        return "";
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public boolean hasError() {
        Object hasErrorObj = request.getAttribute("hasError");
        System.out.println("hasError(): " + hasErrorObj);
        if(hasErrorObj instanceof Boolean hasError && hasError) {
            return true;
        }
        return false;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public String getValue(String name) {
        return request.getParameter(name);
    }
//
//    public Map<String, String[]> getFieldValues() {
//        return fieldValues;
//    }
//
//    public void setFieldValues(Map<String, String[]> fieldValues) {
//        this.fieldValues = fieldValues;
//    }

    public Map<String, String> getErrors() {
        return ((Map<String, String>) request.getAttribute("errors"));
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
