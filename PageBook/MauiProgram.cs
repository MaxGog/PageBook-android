using Microsoft.Extensions.Logging;

using PageBook.Services;
using PageBook.ViewModels;
using PageBook.Views;

namespace PageBook;

public static class MauiProgram
{
	public static MauiApp CreateMauiApp()
	{
		var builder = MauiApp.CreateBuilder();
		builder
			.UseMauiApp<App>()
			.ConfigureFonts(fonts =>
			{
				fonts.AddFont("OpenSans-Regular.ttf", "OpenSansRegular");
				fonts.AddFont("OpenSans-Semibold.ttf", "OpenSansSemibold");
			});

		builder.Services.AddSingleton<MainPage>();
		builder.Services.AddSingleton<EditorPage>();
		builder.Services.AddSingleton<MainViewModel>();
		builder.Services.AddSingleton<EditorViewModel>();
		builder.Services.AddSingleton<NoteStorageService>();
		builder.Services.AddSingleton<FormattingService>();
		builder.Services.AddSingleton<INavigation>(sp => sp.GetRequiredService<INavigation>());
		builder.Services.AddSingleton<IConnectivity>(Connectivity.Current);

#if DEBUG
		builder.Logging.AddDebug();
#endif

		return builder.Build();
	}
}
