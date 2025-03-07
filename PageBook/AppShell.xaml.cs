using PageBook.Views;

namespace PageBook;

public partial class AppShell : Shell
{
	public AppShell()
	{
		InitializeComponent();
		Routing.RegisterRoute(nameof(MainPage), typeof(MainPage));
		Routing.RegisterRoute(nameof(EditorPage), typeof(EditorPage));
		Routing.RegisterRoute(nameof(ToDoListPage), typeof(ToDoListPage));
        Routing.RegisterRoute($"{nameof(ToDoEditorPage)}?id={{itemId}}", typeof(ToDoEditorPage));
	}
}
