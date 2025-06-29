public class Pagamento {
    private String data;
    private double valor;

    public Pagamento(String data, double valor) {
        this.data = data;
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Pagamento em " + data + ": " + valor + " MZN";
    }
}
