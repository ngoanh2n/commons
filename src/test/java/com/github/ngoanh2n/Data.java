package com.github.ngoanh2n;

import java.util.List;

public final class Data {

    public static final class Data1 extends YamlData<Data1> {

        private String f1;
        private String f2;

        public String getF1() {
            return f1;
        }

        public void setF1(String f1) {
            this.f1 = f1;
        }

        public String getF2() {
            return f2;
        }

        public void setF2(String f2) {
            this.f2 = f2;
        }
    }

    public static final class Data2 extends YamlData<Data2> {

        private String f3;
        private List<Data1> f4;

        public String getF3() {
            return f3;
        }

        public void setF3(String f3) {
            this.f3 = f3;
        }

        public List<Data1> getF4() {
            return f4;
        }

        public void setF4(List<Data1> f4) {
            this.f4 = f4;
        }
    }

    public static final class Data3 extends YamlData<Data3> {

        private String f5;
        private Data2 f6;

        public String getF5() {
            return f5;
        }

        public void setF5(String f5) {
            this.f5 = f5;
        }

        public Data2 getF6() {
            return f6;
        }

        public void setF6(Data2 f6) {
            this.f6 = f6;
        }
    }

    @FromResource("com/github/ngoanh2n/Data1.yml")
    public static final class Data4 extends YamlData<Data4> {

        private String f1;
        private String f2;

        public String getF1() {
            return f1;
        }

        public void setF1(String f1) {
            this.f1 = f1;
        }

        public String getF2() {
            return f2;
        }

        public void setF2(String f2) {
            this.f2 = f2;
        }
    }
}
