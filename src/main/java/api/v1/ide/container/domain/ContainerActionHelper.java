package api.v1.ide.container.domain;

public enum ContainerActionHelper {

    COMPILE_COMMAND(new String[]{"sh", "-c", "javac /usr/src/Main.java"}),
    RUN_COMMAND(new String[]{"sh", "-c", "java /usr/src/Main.java"});

    public static final String JAVA_COMPILER_IMG = "openjdk:22-ea-16-jdk";
    private final String[] command;

    ContainerActionHelper(String[] command) {
        this.command = command;
    }

    public static String[] getSaveCommandFormSource(String source) {
        return new String[]{"sh", "-c", "echo '" + source + "' > /usr/src/Main.java"};
    }

    public static String[] getCompileCommand() {
        return COMPILE_COMMAND.command;
    }

    public static String[] getRunCommand() {
        return RUN_COMMAND.command;
    }
}
