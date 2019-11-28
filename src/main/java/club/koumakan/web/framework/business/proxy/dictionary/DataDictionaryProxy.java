package club.koumakan.web.framework.business.proxy.dictionary;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import java.util.List;

@ProxyGen
public interface DataDictionaryProxy {

  static DataDictionaryProxy createProxy(Vertx vertx, String address) { // <5>
    return new DataDictionaryProxyVertxEBProxy(vertx, address);
  }

  void findAll(Handler<AsyncResult<List<DataDictionaryInfo>>> handler);

  void save(DataDictionaryInfo dataDictionaryInfo, Handler<AsyncResult<Long>> handler);

  void edit(DataDictionaryInfo dataDictionaryInfo, Handler<AsyncResult<Void>> handler);

  void delete(List<Long> idList, Handler<AsyncResult<Void>> handler);
}
