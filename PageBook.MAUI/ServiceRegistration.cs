using PageBook.Services;
using PageBook.ViewModels;
using PageBook.Views;

namespace PageBook;

public static class ServiceRegistration
{
    public static MauiAppBuilder ConfigureViewModels(this MauiAppBuilder mauiAppBuilder)
    {
        mauiAppBuilder.Services.AddSingleton<ToDoListViewModel>();
        mauiAppBuilder.Services.AddSingleton<EditorViewModel>();
        mauiAppBuilder.Services.AddSingleton<NotesListViewModel>();
        
        return mauiAppBuilder;
    }
    public static MauiAppBuilder ConfigureServices(this MauiAppBuilder mauiAppBuilder)
    {
        mauiAppBuilder.Services.AddSingleton<ToDoStorageService>();
        mauiAppBuilder.Services.AddSingleton<NoteStorageService>();
        mauiAppBuilder.Services.AddSingleton<FormattingService>();
        mauiAppBuilder.Services.AddSingleton<INavigation>(sp => sp.GetRequiredService<INavigation>());
		mauiAppBuilder.Services.AddSingleton<IConnectivity>(Connectivity.Current);
        
        return mauiAppBuilder;
    }

    public static MauiAppBuilder ConfigureViews(this MauiAppBuilder mauiAppBuilder)
    {
        mauiAppBuilder.Services.AddTransient<ToDoListPage>();
        mauiAppBuilder.Services.AddTransient<ToDoEditorPage>();
        mauiAppBuilder.Services.AddTransient<EditorPage>();
        mauiAppBuilder.Services.AddTransient<NotesListPage>();
        
        return mauiAppBuilder;
    }
}

