package dto.restDTO;

public class RestResultDTO {

    private boolean success;
    private String requestName;

    public RestResultDTO(){

    }

    public RestResultDTO(boolean success, String requestName){
        this.success = success;
        this.requestName = requestName;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean getSuccess(){
        return success;
    }
}
