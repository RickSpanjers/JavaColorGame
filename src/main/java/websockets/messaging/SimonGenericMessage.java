package websockets.messaging;

public class SimonGenericMessage {
    private EnumOperationMessage operationMessage;
    private String data;

    public SimonGenericMessage(EnumOperationMessage msg){
        this.operationMessage = msg;
    }

    public EnumOperationMessage getOperationMessage() {
        return operationMessage;
    }

    public void setOperationMessage(EnumOperationMessage operationMessage) {
        this.operationMessage = operationMessage;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
