package chat.utils;

import chat.client.models.User;

import java.util.Comparator;
import java.util.Objects;

public class UserComparator implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        // if (o1 == null && o2 == null) return -1;
        if (o1 == null && o2 != null) return -1;
        if (o1 != null && o2 == null) return -1;

        String u1 = Objects.requireNonNull(o1).getUsername();
        String u2 = Objects.requireNonNull(o2).getUsername();
        return u1.compareTo(u2);
    }
}
