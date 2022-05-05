package pattern

import pattern.CakePattern.{RedisTemplate, Repository, User, UserRepository}
import reflect.Selectable.reflectiveSelectable

object StructuralTyping {
  class UserService(env: {val repository: Repository
    val redisTemplate: RedisTemplate}) {
    def create(user: User) = {
      println(s"create user=$user")
      env.repository.save(user)
      env.redisTemplate.set(user.toString)
    }
  }

  object Registry {
    lazy val repository = new UserRepository
    lazy val redisTemplate = new RedisTemplate
    lazy val userService = new UserService(this)
  }

  def main(args: Array[String]): Unit = {
    val userService = Registry.userService
    userService.create(User(0, "Tom", 110))
  }
}
