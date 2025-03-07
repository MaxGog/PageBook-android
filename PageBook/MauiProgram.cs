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
			})
			.ConfigureViewModels()
    		.ConfigureViews()
			.ConfigureServices();

#if DEBUG
		builder.Logging.AddDebug();
#endif

		return builder.Build();
	}
}
