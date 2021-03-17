public class TestLogging implements TestLoggingInterface {

    @Log
    @Override
    public Integer calculation(Integer param) {
        return 1;
    }

    @Log
    @Override
    public Double calculation(Integer param2, Double param3) {
        return 2.2;
    }

    @Log
    @Override
    public Integer calculation(Integer param1, Integer param2, String param3) {
        return 1;
    }

    @Override
    public Integer calculation(String param) {
        return 1;
    }


    @Override
    public String toString() {
        return "MyClassImpl{}";
    }
}
