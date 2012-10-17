package org.webharvest.ioc;

import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

import javax.annotation.PostConstruct;

import org.testng.annotations.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import com.google.inject.Singleton;
import com.google.inject.matcher.Matchers;

public class PostConstructListenerTest {

    @Test
    public void invokesPublicNoArgHooks() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bindListener(Matchers.any(), new PostConstructListener());

                bind(WithPostConstructHook.class).in(Singleton.class);
                bind(WithoutPostConstructHook.class).in(Singleton.class);
            }
        });

        injector.getInstance(WithoutPostConstructHook.class);

        final WithPostConstructHook withHook =
            injector.getInstance(WithPostConstructHook.class);
        assertTrue("Post construct hook has not been called",
                withHook.postConstructCalled);
    }

    /**
     * misplaced hook test case - method annotated with @PostConstruct
     * declares input parameters
     */
    @Test
    public void throwsExceptionForHookWithArgs() {
        final Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bindListener(Matchers.any(), new PostConstructListener());

                bind(WithMisplacedPostConstruct.class).in(Singleton.class);
            }
        });
        try {
            injector.getInstance(WithMisplacedPostConstruct.class);
            fail("ProvisionException expected");
        } catch (ProvisionException e) {
            //ok, expected
        }
    }

    private static class WithPostConstructHook {
        private boolean postConstructCalled = false;

        @PostConstruct
        public void shouldBeCalled() {
            postConstructCalled = true;
        }

        public void shouldNotBeCalled() {
            throw new AssertionError("This method should not be called");
        }
    }

    private static class WithoutPostConstructHook {
        public void shouldNotBeCalled() {
            throw new AssertionError("This method should not be called");
        }
    }

    private static class WithMisplacedPostConstruct {
        @PostConstruct
        public void methodWithParameters(final String param1,
                final String param2) {
            // should not be called
        }
    }
}
