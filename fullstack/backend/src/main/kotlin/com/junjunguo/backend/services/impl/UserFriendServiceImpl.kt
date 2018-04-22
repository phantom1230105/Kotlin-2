package com.junjunguo.backend.services.impl

import com.junjunguo.backend.models.api.UserModel
import com.junjunguo.backend.models.entities.UserFriendsEntity
import com.junjunguo.backend.models.enums.FriendStatus
import com.junjunguo.backend.repositories.FriendRepository
import com.junjunguo.backend.repositories.UserRepository
import com.junjunguo.backend.services.UserFriendService
import com.junjunguo.backend.settings.errorHanlder.exceptions.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserFriendServiceImpl(
    private val translateService: TranslateService,
    private val userRepository: UserRepository,
    private val friendRepository: FriendRepository
) :
    UserFriendService {
    override fun addFriend(userId: Long, friendId: Long) {
        val u = userRepository.findById(userId)
        if (!u.isPresent) throw BadRequestException(translateService.translate("ex.user_not_found"))

        val f = userRepository.findById(friendId)
        if (!f.isPresent) throw BadRequestException(translateService.translate("ex.user_not_found"))
        val fe: UserFriendsEntity = if (userId < friendId)
            UserFriendsEntity(u.get(), f.get(), FriendStatus.PENDING_FIRST_SECOND)
        else UserFriendsEntity(f.get(), u.get(), FriendStatus.PENDING_SECOND_FIRST)

        friendRepository.save(fe)
    }

    override fun removeFriend(userId: Long, friendId: Long) {
//        val f = friendRepository.findFriend(
//            if (userId < friendId) userId else friendId,
//            if (userId > friendId) userId else friendId
//        )
//
//        if (!f.isPresent) throw BadRequestException(translateService.translate("ex.user_not_found"))
//
//        friendRepository.delete(f.get())
    }

    override fun confirmFriendRequest(userId: Long, requesterId: Long): UserModel {
        return UserModel(null, null, "", null, null, null)
//        val f = friendRepository.findFriend(
//            if (userId < requesterId) userId else requesterId,
//            if (userId > requesterId) userId else requesterId
//        )
//
//        if (!f.isPresent) throw BadRequestException(translateService.translate("ex.user_not_found"))
//
//        val fe = f.get()
//
//        if (fe.firstUser.id == userId && fe.status == FriendStatus.PENDING_SECOND_FIRST ||
//            fe.secondUser.id == userId && fe.status == FriendStatus.PENDING_FIRST_SECOND
//        ) {
//            f.get().apply { status = FriendStatus.FRIEND }
//            return UserModel(if (fe.firstUser.id == requesterId) fe.firstUser else fe.secondUser)
//        } else throw BadRequestException(translateService.translate("ex.permission_denied"))
    }

    override fun getAllFriends(userId: Long) = null
//        friendRepository.findFriends(userId).get().map { it -> FriendUtil.getFriendModel(it, userId) }

}