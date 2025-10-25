package untitled.src;

public class No <T> {
    private No<T> left;
    private No<T> right;
    private T value;

    public No(No<T> left, No<T> right, T value) {
        this.left = left;
        this.right = right;
        this.value = value;
    }

    public No(T value){
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public No<T> getLeft() {
        return left;
    }

    public void setLeft(No<T> left) {
        this.left = left;
    }

    public No<T> getRight() {
        return right;
    }

    public void setRight(No<T> right) {
        this.right = right;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
