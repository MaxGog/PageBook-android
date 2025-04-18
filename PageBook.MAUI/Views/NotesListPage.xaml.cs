using PageBook.Models;
using PageBook.ViewModels;

namespace PageBook.Views;

public partial class NotesListPage : ContentPage
{
	public NotesListPage()
    {
        InitializeComponent();
        BindingContext = new NotesListViewModel(Navigation);
    }

    [Obsolete]
    protected override async void OnAppearing()
    {
        base.OnAppearing();
        await ((NotesListViewModel)BindingContext).LoadNotesAsync();
    }

    [Obsolete]
    private async void OnNoteSelected(object sender, SelectedItemChangedEventArgs e)
    {
        if (e.SelectedItem as Note != null)
        {
            await ((NotesListViewModel)BindingContext).EditNoteAsync((Note)e.SelectedItem);
            ((ListView)sender).SelectedItem = null;
        }
    }

    private void SearchBarTextChanged(object sender, TextChangedEventArgs e)
    {
    }
}

