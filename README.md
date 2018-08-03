# RoomPagedListAdapterBug

If you use PagedListAdapter and your PagedList is big enough (so big that it starts inserting null padding), scroll position in your RecyclerView will jump every time you submit list with null padding.

Open the app and keep scrolling down. After you've reached 79 next page will load and you'll scroll back to 47. No matter how far down you scroll, you'll always return to 47 when you load next page and call PagedListAdapter#submitList.