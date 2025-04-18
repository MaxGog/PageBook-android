using PageBook.ViewModels;

namespace PageBook.Views;

public partial class CalendarPage : ContentPage
{
    public CalendarPage(CalendarViewModel viewModel)
    {
        InitializeComponent();
        BindingContext = viewModel;
    }
    
    private void DatePicker_DateSelected(object sender, DateChangedEventArgs e)
    {
        if (BindingContext is CalendarViewModel viewModel)
        {
            viewModel.DateSelectedCommand.Execute(null);
        }
    }
}