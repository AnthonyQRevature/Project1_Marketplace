package project.util;

public enum SecurityLevel
{
    GUEST(0),
    USER(1),
    ADMIN(2),
    SUPER_USER(3);

    public final int value;

    SecurityLevel(int value)
    {
        this.value = value;
    }
}
