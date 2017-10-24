package domain.entity

import java.time.LocalDateTime
import com.google.common.net.MediaType

/**
  * 画像のプロパティ
  * @param id 画像ID
  * @param value 画像のプロパティの値
  */
case class PictureProperty(id: PictureId, value: PictureProperty.Value)

object PictureProperty {
  /**
    * 画像のステータス
    * @param value 画像のステータスの値
    */
  sealed abstract class Status(val value: String)

  object Status {
    // 変換に成功した
    case object Success extends Status("success")
    // 変換に失敗した
    case object Failure extends Status("failure")
    // 変換中
    case object Converting extends Status("converting")

    /**
      * 画像のステータスの値から画像のステータスに変換する
      * @param value 画像のステータスの値
      * @return 画像のステータス
      */
    def parse(value: String): Option[Status] =
      value match {
        case Success.value => Some(Success)
        case Failure.value => Some(Failure)
        case Converting.value => Some(Converting)
        case _ => None
      }

  }

  /**
    * 画像のプロパティの値
    * @param status 画像のステータス
    * @param twitterId 投稿者のTwitter ID
    * @param fileName 投稿された画像のファイル名
    * @param contentType 投稿された画像のContent-Type
    * @param overlayText 変換に使われるテキスト
    * @param overlayTextSize 変換に使われるテキストのサイズ
    * @param originalFilepath 変換前のファイルのパス
    * @param convertedFilepath 変換後のファイルのパス
    * @param createdTime 投稿された時間
    */
  case class Value(
                    status: Status,
                    twitterId: TwitterId,
                    fileName: String,
                    contentType: MediaType,
                    overlayText: String,
                    overlayTextSize: Int,
                    originalFilepath: Option[String],
                    convertedFilepath: Option[String],
                    createdTime: LocalDateTime
                  )

}
