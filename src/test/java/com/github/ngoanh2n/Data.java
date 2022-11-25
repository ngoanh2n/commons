package com.github.ngoanh2n;

import java.util.List;

public final class Data {
    public static final class Data1 extends YamlData<Data1> {
        private String k1;
        private String k2;

        public String getK1() {
            return k1;
        }

        public void setK1(String k1) {
            this.k1 = k1;
        }

        public String getK2() {
            return k2;
        }

        public void setK2(String k2) {
            this.k2 = k2;
        }
    }

    public static final class Data2 extends YamlData<Data2> {
        private String k3;
        private List<Data1> k4;

        public String getK3() {
            return k3;
        }

        public void setK3(String k3) {
            this.k3 = k3;
        }

        public List<Data1> getK4() {
            return k4;
        }

        public void setK4(List<Data1> k4) {
            this.k4 = k4;
        }
    }

    public static final class Data3 extends YamlData<Data3> {
        private String k5;
        private Data2 k6;

        public String getK5() {
            return k5;
        }

        public void setK5(String k5) {
            this.k5 = k5;
        }

        public Data2 getK6() {
            return k6;
        }

        public void setK6(Data2 k6) {
            this.k6 = k6;
        }
    }

    @YamlFrom(resource = "com/github/ngoanh2n/Data1.yml")
    public static final class Data4 extends YamlData<Data4> {
        private String k1;
        private String k2;

        public String getK1() {
            return k1;
        }

        public void setK1(String k1) {
            this.k1 = k1;
        }

        public String getK2() {
            return k2;
        }

        public void setK2(String k2) {
            this.k2 = k2;
        }
    }

    @YamlFrom(file = "src/test/resources/com/github/ngoanh2n/Data1.yml")
    public static final class Data5 extends YamlData<Data5> {
        private String k1;
        private String k2;

        public String getK1() {
            return k1;
        }

        public void setK1(String k1) {
            this.k1 = k1;
        }

        public String getK2() {
            return k2;
        }

        public void setK2(String k2) {
            this.k2 = k2;
        }
    }
}
