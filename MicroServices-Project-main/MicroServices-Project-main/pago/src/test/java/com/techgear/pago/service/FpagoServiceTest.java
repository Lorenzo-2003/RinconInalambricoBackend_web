package com.techgear.pago.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.techgear.pago.model.Fpago;
import com.techgear.pago.repository.FpagoRepository;
import com.techgear.pago.repository.FacturaRepository;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FpagoServiceTest {

    @Mock
    private FpagoRepository fpagoRepository;

    @Mock
    private FacturaRepository facturaRepository; // 👈 se necesita porque FpagoService lo @Autowired

    @InjectMocks
    private FpagoService fpagoService;

    @Test
    public void testFindAll() {
        when(fpagoRepository.findAll())
            .thenReturn(List.of(new Fpago(1, "Test")));

        // 👇 usa el método REAL de tu service
        List<Fpago> fpagos = fpagoService.getAllFpagos();

        assertNotNull(fpagos);
        assertEquals(1, fpagos.size());
        verify(fpagoRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        int id = 1;
        Fpago fpago = new Fpago(1, "Test");

        when(fpagoRepository.findById(id)).thenReturn(Optional.of(fpago));

        Fpago found = fpagoService.getFpago(id);

        assertNotNull(found);
        assertEquals(id, found.getId());
        verify(fpagoRepository, times(1)).findById(id);
    }

    @Test
    public void testSave() {
        Fpago fpago = new Fpago(1, "Test");

        when(fpagoRepository.save(fpago)).thenReturn(fpago);

        Fpago saved = fpagoService.saveFpago(fpago);

        assertNotNull(saved);
        assertEquals(1, saved.getId());
        verify(fpagoRepository, times(1)).save(fpago);
    }

    @Test 
    public void testDeleteByCodigo() {
        Integer id = 1;

        doNothing().when(fpagoRepository).deleteById(id);

        fpagoService.deleteFpago(id);

        verify(fpagoRepository, times(1)).deleteById(id);
    }
}
