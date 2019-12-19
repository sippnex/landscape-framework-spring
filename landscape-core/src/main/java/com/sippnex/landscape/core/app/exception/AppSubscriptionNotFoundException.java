package com.sippnex.landscape.core.app.exception;

import org.springframework.data.annotation.Immutable;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

@Immutable
public class AppSubscriptionNotFoundException extends AbstractThrowableProblem {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

    public AppSubscriptionNotFoundException(String appId) {
        super(URI.create(""), "App-Subscription not found", Status.NOT_FOUND, String.format("App-Subscription for app %s not found", appId));
    }

}
