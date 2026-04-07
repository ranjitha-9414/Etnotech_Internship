package com.ranjitha.code_execution_engine.executor;

public class ExecutionResult {

    private String output;
    private String error;
    private boolean success;

    public ExecutionResult(String output, String error, boolean success) {
        this.output = output;
        this.error = error;
        this.success = success;
    }

    public String getOutput() {
        return output;
    }

    public String getError() {
        return error;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean success() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'success'");
    }

    public String error() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'error'");
    }

    public String output() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'output'");
    }
}