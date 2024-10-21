package keystrokesmod.utility;

import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuiConnectingMsg {
    protected static final Random random = new Random();
    private final List<Data> dataList;
    private String tmpResult = null;

    public GuiConnectingMsg() {
        this.dataList = new ArrayList<>();
    }

    public void update(final Data data) {
        int index = dataList.indexOf(data);

        if (index == -1) {
            dataList.add(data);
        } else {
            dataList.set(index, data);
        }
        tmpResult = null;
    }

    public void remove(final Data data) {
        dataList.remove(data);
        tmpResult = null;
    }

    @Override
    public String toString() {
        if (dataList.isEmpty()) return "";
        if (tmpResult != null) return tmpResult;

        final StringBuilder result = new StringBuilder(dataList.size());

        for (Data data : dataList) {
            String str = data.get();
            if (str.isEmpty()) continue;

            result.append(str);
        }

        final String strResult = result.toString();
        tmpResult = strResult;
        return strResult;
    }

    public String toString(String fromString) {
        if (toString().isEmpty())
            return fromString;
        return toString();
    }

    public static class Data {
        private final int id;
        private StringBuilder stringData;
        
        public Data() {
            this.id = MathHelper.getRandomIntegerInRange(random, 1, Integer.MAX_VALUE);
            this.stringData = new StringBuilder();
        }

        public String get() {
            return stringData.toString();
        }

        public void set(final String message) {
            this.stringData = new StringBuilder(message);
        }

        public void append(final String message) {
            stringData.append(message);
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (!(o instanceof Data)) return false;
            Data data = (Data) o;
            return id == data.id;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }
}
