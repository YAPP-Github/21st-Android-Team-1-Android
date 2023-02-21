package com.yapp.buddycon.domain.usecase.delete

import com.yapp.buddycon.domain.model.DeleteCouponResult
import com.yapp.buddycon.domain.repository.DeleteCouponRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCouponUseCase @Inject constructor(
    private val deleteCouponRepository: DeleteCouponRepository
) {
    operator fun invoke(id: Int): Flow<DeleteCouponResult> =
        deleteCouponRepository.deleteCoupon(id)
}