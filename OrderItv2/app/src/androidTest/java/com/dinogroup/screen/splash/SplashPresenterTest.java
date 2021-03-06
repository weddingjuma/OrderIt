package com.dinogroup.screen.splash;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static com.dinogroup.test.util.FlowTestHelper.createFlow;
import static com.dinogroup.test.util.ViewTestHelper.mockView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import flow.Flow;
import com.dinogroup.actionbar.ActionBarConfig;
import com.dinogroup.actionbar.ActionBarOwner;
import com.dinogroup.screen.login.LoginScreen;
import com.dinogroup.screen.register.RegisterScreen;
import com.dinogroup.test.util.FlowTestHelper.MockFlowListener;

@RunWith(RobolectricTestRunner.class)
public class SplashPresenterTest {

	@Mock SplashScreen screen;
	@Mock ActionBarOwner actionBarOwner;

	@Captor ArgumentCaptor<ActionBarConfig> actionBarConfigCaptor;

	private SplashView view;
	private SplashPresenter presenter;
	private MockFlowListener flowListener;

	@Before
	public void before() throws Exception {
		MockitoAnnotations.initMocks(this);

		view = mockView(SplashView.class);
		flowListener = new MockFlowListener();
		Flow flow = createFlow(screen, flowListener);

		presenter = new SplashPresenter(flow, actionBarOwner);
		presenter.takeView(view);
	}

	@Test
	public void shouldHideActionBarOnLoad() throws Exception {
		verify(actionBarOwner).setConfig(actionBarConfigCaptor.capture());

		ActionBarConfig actionBarConfig = actionBarConfigCaptor.getValue();
		assertThat(actionBarConfig.isVisible()).isFalse();
	}

	@Test
	public void shouldGoToLoginScreen() throws Exception {
		presenter.login();

		assertThat(flowListener.lastDirection).isEqualTo(Flow.Direction.FORWARD);
		assertThat(flowListener.lastScreen).isInstanceOf(LoginScreen.class);
	}

	@Test
	public void shouldGoToRegisterScreen() throws Exception {
		presenter.register();

		assertThat(flowListener.lastDirection).isEqualTo(Flow.Direction.FORWARD);
		assertThat(flowListener.lastScreen).isInstanceOf(RegisterScreen.class);
	}
}
