package backend.domain.model;

public class Admin extends User {

    public Admin(
            Long id,
            String email,
            String password,
            String firstName,
            String lastName
    ) {
        super(id, email, password, firstName, lastName);
    }

    public Admin() {
        super();
    }
}
