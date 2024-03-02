import ComposeApp
import SwiftUI

@main
struct iOSApp: SwiftUI.App {
    init() {
		MainViewControllerKt.doInitKoin()
    }

    var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}