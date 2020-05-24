# ReactiveMongo Transaction test
---
##### Mongodb database version: MongoDB 4.2.6 Enterprise
### To do
0. Create foo and bar collection in database.
1. Go to src/test/scala/MongoTest.scala
2. Set uri : `val mongoUri = "uri"`
3. Run sbt 
4. Run test

---
#### Code

If we create `many` insert it creates `TransientTransactionError` error.

```scala
val future = for {
    foo <- colFo.insert(true).many(Seq(BSONDocument("name" -> "Red"))) <<<------ many insert
    _ <- colBar.insert(true).one(BSONDocument("name" -> "Red"))
   } yield foo
   
```

#### Error

```javascript
 - should create transaction *** FAILED ***
[info]   reactivemongo.api.commands.CommandError$DefaultCommandError: CommandError[code=251, errmsg=Given transaction number 1 does not match any in-progress transactions. The active transaction number is -1, doc: {
[info]   "errorLabels": [
[info]     "TransientTransactionError"
[info]   ],
[info]   "operationTime": Timestamp(1590340759, 2),
[info]   "ok": 0.0,
[info]   "errmsg": "Given transaction number 1 does not match any in-progress transactions. The active transaction number is -1",
[info]   "code": 251,
[info]   "codeName": "NoSuchTransaction",
[info]   "$clusterTime": {
[info]     "clusterTime": Timestamp(1590340759, 2),
[info]     "signature": {
[info]       "hash": BSONBinary(GenericBinarySubtype, size = 20),
[info]       "keyId": NumberLong(6778831253614362625)
[info]     }
[info]   }
[info] }]

```
