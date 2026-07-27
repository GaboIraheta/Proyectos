--- Edwin Oswaldo Guzman Melendez 00371023
-- Dim Product

SELECT 
    P.ProductID AS ProductKey,
    P.ProductNumber,
    P.Name AS ProductName,
    P.Color,
    P.StandardCost,
    P.ListPrice,
    P.Size,
    
    -- Cruce con Subcategoría
    PS.ProductSubcategoryID,
    PS.Name AS SubcategoryName,
    
    -- Cruce con Categoría
    PC.ProductCategoryID,
    PC.Name AS CategoryName

FROM Production.Product AS P
-- LEFT JOIN para traer la subcategoría si el producto tiene una asignada
LEFT JOIN Production.ProductSubcategory AS PS 
    ON P.ProductSubcategoryID = PS.ProductSubcategoryID
-- LEFT JOIN para traer la categoría correspondiente a esa subcategoría
LEFT JOIN Production.ProductCategory AS PC 
    ON PS.ProductCategoryID = PC.ProductCategoryID;

--- Stephanie Aracely Echeverria Cuellar 00197822
-- Dim Customer

WITH ClientesDirecciones AS (
    SELECT 
        c.CustomerID AS CustomerKey,
        c.AccountNumber AS CustomerID,
        CONCAT(p.FirstName, ' ', p.LastName) AS Customer,
        a.City,
        sp.Name AS StateProvince,
        cr.Name AS CountryRegion,
        a.PostalCode,
        ROW_NUMBER() OVER (PARTITION BY c.CustomerID ORDER BY a.AddressID) AS RowNum
    FROM Sales.Customer c
    INNER JOIN Person.Person p ON c.PersonID = p.BusinessEntityID
    INNER JOIN Person.BusinessEntityAddress bea ON p.BusinessEntityID = bea.BusinessEntityID
    INNER JOIN Person.Address a ON bea.AddressID = a.AddressID
    INNER JOIN Person.StateProvince sp ON a.StateProvinceID = sp.StateProvinceID
    INNER JOIN Person.CountryRegion cr ON sp.CountryRegionCode = cr.CountryRegionCode
)
SELECT -1 AS CustomerKey, '[Not Applicable]' AS CustomerID, '[Not Applicable]' AS Customer, '[Not Applicable]' AS City, '[Not Applicable]' AS StateProvince, '[Not Applicable]' AS CountryRegion, '[Not Applicable]' AS PostalCode
UNION ALL
SELECT CustomerKey, CustomerID, Customer, City, StateProvince, CountryRegion, PostalCode
FROM ClientesDirecciones
WHERE RowNum = 1; 

-- Diego Benjamin Garcia Alfaro 00088023
-- Dim Sales Order

SELECT
	CASE
        WHEN h.OnlineOrderFlag = 1 THEN 'Internet'
        ELSE 'Reseller'
    END AS Channel,
    d.SalesOrderDetailID AS SalesOrderLineKey,
    h.SalesOrderNumber AS SalesOrder,
    d.SalesOrderDetailID AS SalesOrderLine
FROM Sales.SalesOrderDetail AS d
INNER JOIN Sales.SalesOrderHeader AS h
    ON d.SalesOrderID = h.SalesOrderID;

-- Gabriel Ernesto Iraheta Guardado 00021223
-- Dim Sales Territory

SELECT
    st.SalesTerritoryKey,
    st.SalesTerritoryRegion AS Region,
    st.SalesTerritoryCountry AS Country,
    st.SalesTerritoryGroup AS [Group],
    COUNT(DISTINCT fs.SalesOrderNumber) AS TotalOrder,
    SUM(fs.SalesAmount) AS TotalVentas,
    SUM(fs.SalesAmount - fs.TotalProductCost) AS TotalProfit,
    ROUND(
        (SUM(fs.SalesAmount - fs.TotalProductCost) / SUM(fs.SalesAmount)) * 100, 2) AS ProfitMargin
FROM DimSalesTerritory AS st
LEFT JOIN FactResellerSales AS frs
    ON st.SalesTerritoryKey = frs.SalesTerritoryKey
LEFT JOIN FactInternetSales AS fis
    ON st.SalesTerritoryKey = fs.SalesTerritoryKey
GROUP BY
    st.SalesTerritoryKey,
    st.SalesTerritoryRegion,
    st.SalesTerritoryCountry,
    st.SalesTerritoryGroup
ORDER BY TotalSales DESC;