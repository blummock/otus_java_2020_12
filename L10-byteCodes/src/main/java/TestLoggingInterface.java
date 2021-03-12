public interface TestLoggingInterface {

    @Log
    Integer calculation(Integer param);
    @Log
    Double calculation(Integer param2, Double param3);
    @Log
    Integer calculation(Integer param1, Integer param2, String param3);

    Integer calculation(String param);
}
