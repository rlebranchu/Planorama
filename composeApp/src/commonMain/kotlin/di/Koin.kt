package di

import org.koin.core.context.startKoin
import org.koin.dsl.module
import services.BookingService
import ui.booking.BookingViewModel

fun initKoin() =
    startKoin() {
        modules(
            viewModels,
        )
    }

val viewModels = module {
    single { BookingService() }

    factory { BookingViewModel(bookingService = get()) }
}