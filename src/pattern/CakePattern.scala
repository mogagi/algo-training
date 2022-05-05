package pattern

import pattern.CakePattern.Registry

object CakePattern {
  case class User(id: Long, name: String, phone: Int)

  trait Repository {
    def save(user: User): Unit
  }

  class UserRepository extends Repository {
    override def save(user: User): Unit = println(s"save user=$user")
  }

  class RedisTemplate {
    def set(str: String): Unit = println(s"set str=$str")
  }

  trait RepositoryComponent {
    val repository: Repository
  }

  trait TemplateComponent {
    val redisTemplate: RedisTemplate
  }

  trait ServiceComponent {
    this: RepositoryComponent with TemplateComponent =>
    val userService: UserService

    class UserService {
      def create(user: User) = {
        println(s"create user=$user")
        repository.save(user)
        redisTemplate.set(user.toString)
      }
    }
  }

  object Registry extends ServiceComponent with RepositoryComponent with TemplateComponent {
    override val userService: Registry.UserService = UserService()
    override val repository: Repository = UserRepository()
    override val redisTemplate: RedisTemplate = RedisTemplate()
  }

  def main(args: Array[String]): Unit = {
    val userService = Registry.userService
    userService.create(User(0, "Tom", 110))
  }
}
