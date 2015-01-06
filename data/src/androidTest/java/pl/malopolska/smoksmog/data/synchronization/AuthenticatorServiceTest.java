package pl.malopolska.smoksmog.data.synchronization;

import android.content.Intent;
import android.test.ServiceTestCase;

public class AuthenticatorServiceTest extends ServiceTestCase<AuthenticatorService> {

    public AuthenticatorServiceTest() {
        super(AuthenticatorService.class);
    }

    public void testOnCreationAndBind() throws Exception {
        bindService(new Intent());
    }
}