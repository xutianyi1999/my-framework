package club.koumakan.web.framework;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.vavr.Function1;
import io.vertx.core.Handler;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;

public class ReceiveHandler {

  public static final Map<String, String> HTTP_JSON_HEADER = Map.of(
    HttpHeaderNames.CONTENT_TYPE.toString(),
    HttpHeaderValues.APPLICATION_JSON.toString() + "; charset=utf-8"
  );

  private static final Function1<Map<String, String>, Function1<Handler<RoutingContext>, Handler<RoutingContext>>> SET_RESPONSE_HEADERS =
    httpHeaders -> requestHandler -> routingContext -> {
      httpHeaders.forEach(routingContext.response()::putHeader);
      requestHandler.handle(routingContext);
    };

  private static final Function1<Handler<RoutingContext>, Handler<RoutingContext>> JSON_HEADER_FUNCTION =
    SET_RESPONSE_HEADERS.apply(HTTP_JSON_HEADER);

  private Router router;
  private String parentsRoute;

  private ReceiveHandler(Router router, String parentsRoute) {
    this.router = router;
    this.parentsRoute = parentsRoute;
  }

  public static ReceiveHandler receiveHandler(Router router, String route) {
    return new ReceiveHandler(router, route);
  }

  public static ReceiveHandler receiveHandler(Router router) {
    return receiveHandler(router, "");
  }

  public ReceiveHandler get(String route, Handler<RoutingContext> requestHandler) {
    router.get(parentsRoute + route).handler(JSON_HEADER_FUNCTION.apply(requestHandler));
    return this;
  }

  public ReceiveHandler get(String route, Handler<RoutingContext> requestHandler, Map<String, String> httpHeaders) {
    router.get(parentsRoute + route).handler(SET_RESPONSE_HEADERS.apply(httpHeaders).apply(requestHandler));
    return this;
  }

  public ReceiveHandler post(String route, Handler<RoutingContext> requestHandler) {
    router.post(parentsRoute + route).handler(JSON_HEADER_FUNCTION.apply(requestHandler));
    return this;
  }

  public ReceiveHandler post(String route, Handler<RoutingContext> requestHandler, Map<String, String> httpHeaders) {
    router.post(parentsRoute + route).handler(SET_RESPONSE_HEADERS.apply(httpHeaders).apply(requestHandler));
    return this;
  }
}
