package module;

public class Batch {
    private String batch_name;

    public Batch() {
    }

    public Batch(String batch_name) {
        this.batch_name = batch_name;
    }

    public String getBatch_name() {
        return batch_name;
    }

    public void setBatch_name(String batch_name) {
        this.batch_name = batch_name;
    }

    @Override
    public String toString() {
        return "Batch{" +
                "batch_name='" + batch_name + '\'' +
                '}';
    }
}
