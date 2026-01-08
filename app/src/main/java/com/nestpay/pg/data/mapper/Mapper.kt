package com.nestpay.pg.data.mapper

import android.graphics.Movie
import com.nestpay.pg.data.model.local.OrderEntity
import com.nestpay.pg.data.model.remote.ApiEntity
import com.nestpay.pg.data.model.remote.ApiPayEntity
import com.nestpay.pg.data.model.remote.ApiPayReadyEntity
import com.nestpay.pg.data.model.remote.GithubEntity
import com.nestpay.pg.domain.model.local.Order
import com.nestpay.pg.domain.model.remote.ApiPayReadyRepo
import com.nestpay.pg.domain.model.remote.ApiPayRepo
import com.nestpay.pg.domain.model.remote.ApiRepo
import com.nestpay.pg.domain.model.remote.Github

/**
 * Data Entity to Domain Model
 * Data Layer 에서는 Data Entity 로 받아서 사용하지만, Domain, Presentation Layer 에서는 Data Model 로 사용한다.
 * 즉, Mapper 는 Data Layer 에 존재하면서 다른 계층으로 Data 를 전달할 때, 사용하는 Data Model 에 맞춰서 변환하여 던지는 역할.
 *
 * @param movies Data Model 로 변환 할 Entity
 * @return Data Model
 */

object Mapper {

    /*샘플*/
    fun mapperToGithub(github: List<GithubEntity>?): List<Github>? {
        return github?.map {
            Github(
                it.name,
                it.id,
                it.created_at,
                it.url
            )
        }
    }

    /*일반 API 매핑*/
    fun mapperToApiInfoRepo(apiEntity: ApiEntity?): ApiRepo? {
        return apiEntity?.let {
            ApiRepo(
                result = it.result,
                data = it.data
            )
        }
    }

    /*결제준비 API 매핑*/
    fun mapperToApiPayReadyRepo(apiPayReadyEntity: ApiPayReadyEntity?): ApiPayReadyRepo? {
        return apiPayReadyEntity?.let {
            ApiPayReadyRepo(
                result = it.result,
                link = it.link
            )
        }
    }

    /*결제준비 API 매핑*/
    fun mapperToApiPayRepo(apiPayEntity: ApiPayEntity?): ApiPayRepo? {
        return apiPayEntity?.let {
            ApiPayRepo(
                result = it.result,
                pay = it.pay
            )
        }
    }

    /*주문내역 Room DB 매핑*/
    fun mapperToLocalOrderInfo(orderEntity: List<OrderEntity>): List<Order> {
        return orderEntity.map {
            Order(
                idx = it.idx,
                payDay = it.payDay,
                franchiseName = it.franchiseName,
                productName = it.productName,
                productPrice = it.productPrice,
                productMsg = it.productMsg
            )
        }
    }

    /*주문내역 Room DB 저장 매핑*/
    /*fun mapperToLocalOrderInsert(orderEntity: OrderEntity) {
        OrderEntity(
            idx = orderEntity.idx,
            payDay = orderEntity.payDay,
            franchiseName = orderEntity.franchiseName,
            productName = orderEntity.productName,
            productPrice = orderEntity.productPrice,
            productMsg = orderEntity.productMsg
        )
    }*/

    fun mapperToLocalOrderInsert(orderEntity: OrderEntity) {
        OrderEntity(
            orderEntity.idx,
            orderEntity.payDay,
            orderEntity.franchiseName,
            orderEntity.productName,
            orderEntity.productPrice,
            orderEntity.productMsg
        )
    }
}
