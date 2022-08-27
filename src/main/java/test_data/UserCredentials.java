package test_data;

import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class UserCredentials {

    private static Stream<Arguments> credentialsTestData() {
        return Stream.of(
                Arguments.of("janusz@test.pl","QWERTY123","Nieznany adres e-mail. Proszę sprawdzić ponownie lub wypróbować swoją nazwę użytkownika."),
                Arguments.of("janusz","QWERTY123","Błąd: Brak janusz wśród zarejestrowanych w witrynie użytkowników. Jeśli nie masz pewności co do nazwy użytkownika, użyj adresu e-mail."),
                Arguments.of("","QWERTY123","Błąd: Nazwa użytkownika jest wymagana."),
                Arguments.of("janusz@test.pl","","Błąd: Hasło jest puste.")
                );
    }
}
