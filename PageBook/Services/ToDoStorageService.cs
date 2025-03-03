using System.Collections.ObjectModel;

using SQLite;

using PageBook.Models;

namespace PageBook.Services;

public class ToDoStorageService : IAsyncDisposable
{
    private readonly string databasePath;
    private SQLiteAsyncConnection connection;

    public ToDoStorageService()
    {
        databasePath = Path.Combine(FileSystem.AppDataDirectory, "todo.db");
        InitializeDatabase();
    }

    private void InitializeDatabase()
    {
        connection = new SQLiteAsyncConnection(databasePath);
        connection.CreateTableAsync<ToDo>().Wait();
    }

    public async Task<ObservableCollection<ToDo>> GetAllToDoItemsAsync()
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

    public async Task<ToDo> GetToDoItemAsync(string id)
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

    public async Task SaveToDoItemAsync(ToDo item)
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

    public async Task DeleteToDoItemAsync(string id)
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

    public async Task UpdateToDoItemAsync(ToDo item)
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

    private void InitializeConnection()
    {
        if (connection == null)
        {
            InitializeDatabase();
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