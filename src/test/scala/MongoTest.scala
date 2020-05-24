import org.scalatest.flatspec.AsyncFlatSpec
import reactivemongo.api.MongoDriver
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

class MongoTest extends AsyncFlatSpec {


  import reactivemongo.api.{DefaultDB, MongoConnection}

  val mongoUri = "xxxxxx"


  val driver = new MongoDriver

  val database = for {
    uri <- Future.fromTry(MongoConnection.parseURI(mongoUri))
    con: MongoConnection = driver.connection(uri, strictUri = false).get
    dn <- Future(uri.db.get)
    db <- con.database(dn)
  } yield db

  def test() = {
    database.flatMap {
      defaultDb =>
        defaultDb.startSession().flatMap {
          case None => Future.failed(new Exception("No replica"))
          case Some(sessionDb) =>
            val stTanx: Option[DefaultDB] = sessionDb.startTransaction(None)

            stTanx match {
              case None => Future.failed(new Exception("None"))
              case Some(tnxDb) =>
                val colFo = tnxDb.collection[BSONCollection]("foo")
                val colBar = tnxDb.collection[BSONCollection]("bar")

                val future = for {
                  foo <- colFo.insert(true).many(Seq(BSONDocument("name" -> "Red")))
                  _ <- colBar.insert(true).one(BSONDocument("name" -> "Red"))

                } yield foo

                future.flatMap {
                  x =>
                    println(x)
                    tnxDb.commitTransaction().flatMap {
                      _ =>
                        sessionDb.endSession()
                    }

                }


            }

        }
    }
  }


  it should "create transaction" in {
    test().map {
      _ =>
        succeed
    }
  }
}
