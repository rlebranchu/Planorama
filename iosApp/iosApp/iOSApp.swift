import SwiftUI

@main
struct iOSApp: App {

    init() {
        DIHelperKt.doInitKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}