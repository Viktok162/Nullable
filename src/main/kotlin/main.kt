import WallService.add
import WallService.postsAny
import WallService.sizeOfPosts
import WallService.update

fun main() {
    val photo = Attachment.Photo(id = 22, ownerId = 22, photo604 = " link to the photo")
    val video = Attachment.Video(id = 33, ownerId = 33, duration = 10)
    val audio = Attachment.Audio(id = 44, ownerId = 44, title = " New audio", url = " link to the audio")

    val photoAttachment = Attachment.PhotoAttachment(photo)
    val videoAttachment = Attachment.VideoAttachment(video)
    val audioAttachment = Attachment.AudioAttachment(audio)
    println("type = ${photoAttachment.type}, ${photoAttachment.photo}")
    println("type = ${videoAttachment.type}, ${videoAttachment.video}")
    println("type = ${audioAttachment.type}, ${audioAttachment.audio}")


    val postIni = Post(0, likes = Post.Likes(10), attachments = listOf(photoAttachment, videoAttachment, audioAttachment))
    //postIni.showDataPost()

    add(postIni)
    add(postIni)
    add(postIni)
    add(postIni)
    add(postIni)

    println("posts size is = ${sizeOfPosts()}")
    postsAny(1)

    val sample = Post(3, copyright = "Super", likes = Post.Likes(500, canLike = true))
    println(update(sample))
}

abstract class Attachment(open val type: String) {

    data class Photo(
        val id: Int = 1,
        val ownerId: Int = 1,
        val photo130: String = " some link to the object",
        val photo604: String = " another link to the object"
    )

    data class PhotoAttachment(
        val photo: Photo
    ) : Attachment("photo"){}

    data class Video(
        val id: Int = 1,
        val ownerId: Int = 1,
        val title: String = " A Funny Video",
        val duration: Int = 30
    )

    data class VideoAttachment(val video: Video) : Attachment("video")

    data class Audio(
        val id: Int = 1,
        val ownerId: Int = 1,
        val artist: String = " Names of artists",
        val title: String = " Title of audio",
        val duration: Int = 5,
        val url: String = " link to the object"
    )

    data class AudioAttachment(val audio: Audio) : Attachment("audio")

}

data class Post(
    var id: Int? = 0,
    val ownerId: Int = 0,
    val formId: Int? = 0,
    val createdBy: Int = 0,
    val text: String? = "Hello!",
    val copyright: String = "protected",
    val postType: String = "brief story",
    val canPin: Boolean? = false,
    val canDelete: Boolean = false,
    val canEdit: Boolean = false,
    val likes: Likes = Likes(),
    val attachments: List<Attachment> = emptyList()
) {
    init {
        println("\nInit: New instance created, id = $id")
    }

    fun showDataPost() {
        println(
            "Данные объекта post: id = $id, ownerId = $ownerId, formId = $formId," +
                    " copyright = $copyright, canPin = $canPin, $text, likes = ${likes.count}, canLike = ${likes.canLike}"
        )
    }

    class Likes(
        val count: Int = 0,
        val userLikes: Boolean = false,
        val canLike: Boolean = false,
        val canPublish: Boolean = false
    )
}

object WallService {
    var posts = mutableListOf<Post>()
    private var number: Int = 0

    fun add(post: Post): Post {
        val p = post.copy(id = ++number)
        posts += p
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for (i in posts.indices) {
            if (post.id == posts[i].id) {
                posts[i] = post
                //posts[i].showDataPost()
                return true
            }
        }
        return false
    }

    fun sizeOfPosts(): Int {
        return posts.size
    }

    fun postsAny(index: Int) {
        println("Element $index of posts:")
        posts[index].showDataPost()
    }

    fun resetNumber() {
        number = 0
    }

    fun resetPosts() {
        posts.clear()
    }
}
