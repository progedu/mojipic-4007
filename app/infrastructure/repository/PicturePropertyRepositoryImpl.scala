package infrastructure.repository

import domain.entity.{PictureId, PictureProperty}
import domain.repository.PicturePropertyRepository
import scalikejdbc._

import scala.concurrent.Future
import scala.util.Try

class PicturePropertyRepositoryImpl extends PicturePropertyRepository {

  def create(value: PictureProperty.Value): Future[PictureId] =
    Future.fromTry(Try {
      using(DB(ConnectionPool.borrow())) { db =>
        db.localTx { implicit session =>
          val sql =
            sql"""INSERT INTO picture_properties (
                 | status,
                 | twitter_id,
                 | file_name,
                 | content_type,
                 | overlay_text,
                 | overlay_text_size,
                 | original_filepath,
                 | converted_filepath,
                 | created_time
                 | ) VALUES (
                 | ${value.status.value},
                 | ${value.twitterId.value},
                 | ${value.fileName},
                 | ${value.contentType.toString},
                 | ${value.overlayText},
                 | ${value.overlayTextSize},
                 | ${value.originalFilepath.getOrElse(null)},
                 | ${value.convertedFilepath.getOrElse(null)},
                 | ${value.createdTime}
                 | )
              """.stripMargin
          PictureId(sql.updateAndReturnGeneratedKey().apply())
        }
      }
    })
}
