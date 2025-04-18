using System.Collections.ObjectModel;

using SQLite;

using PageBook.Models;

namespace PageBook.Services;

public class EventStorageService : IAsyncDisposable
{
    private readonly string databasePath;
    private SQLiteAsyncConnection connection;

    public EventStorageService()
    {
        databasePath = Path.Combine(FileSystem.AppDataDirectory, "events.db");
        InitializeDatabase();
    }
    private void InitializeConnection()
    {
        if (connection == null)
        {
            InitializeDatabase();
        }
    }

    private void InitializeDatabase()
    {
        connection = new SQLiteAsyncConnection(databasePath);
        connection.CreateTableAsync<ToDo>().Wait();
    }

    public async Task<ObservableCollection<ToDo>> GetAllEventsAsync()
    {
        try
        {
            InitializeConnection();
            var todo = await connection.Table<ToDo>()
                .OrderBy(n => n.CreatedAt)
                .ToListAsync();
            return new ObservableCollection<ToDo>(todo);
        }
        catch (SQLiteException ex)
        {
            throw new Exception($"Ошибка при получении заметок: {ex.Message}");
        }
    }

    public async Task<ToDo> GetEventAsync(string id)
    {
        try
        {
            InitializeConnection();
            return await connection.Table<ToDo>()
                .Where(n => n.Id == id)
                .FirstOrDefaultAsync();
        }
        catch (SQLiteException ex)
        {
            throw new Exception($"Ошибка при получении заметки: {ex.Message}");
        }
    }

    public async Task SaveEventAsync(ToDo item)
    {
        try
        {
            InitializeConnection();
            if (string.IsNullOrEmpty(item.Id))
            {
                item.Id = Guid.NewGuid().ToString();
                await connection.InsertAsync(item);
            }
            else
            {
                await connection.UpdateAsync(item);
            }
        }
        catch (SQLiteException ex)
        {
            throw new Exception($"Ошибка при сохранении заметки: {ex.Message}");
        }
    }

    public async Task DeleteEventAsync(string id)
    {
        try
        {
            InitializeConnection();
            await connection.DeleteAsync<ToDo>(id);
        }
        catch (SQLiteException ex)
        {
            throw new Exception($"Ошибка при удалении заметки: {ex.Message}");
        }
    }

    public async Task UpdateEventAsync(ToDo item)
    {
        try
        {
            InitializeConnection();
            await connection.UpdateAsync(item);
        }
        catch (SQLiteException ex)
        {
            throw new Exception($"Ошибка при обновлении заметки: {ex.Message}");
        }
    }

    public async ValueTask DisposeAsync()
    {
        if (connection != null)
        {
            await connection.CloseAsync();
            //connection.Dispose();
            connection = null;
        }
    }
}