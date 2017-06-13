package filestreamer12.utils;

/**
 * Created by Karol Pokomeda on 2017-06-13.
 */
public interface Matrix {
    boolean setValue(int rowIndex, int columnIndex, double value);
    double getValue(int rowIndex, int columnIndex);
    double[][] toArray();
}
